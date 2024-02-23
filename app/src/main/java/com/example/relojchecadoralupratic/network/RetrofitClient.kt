import com.example.relojchecadoralupratic.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitClient {
    private const val BASE_URL = "https://tu.url.base/aqui/"

    // Configurar un TrustManager personalizado para aceptar todos los certificados
    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })

    // Crear un cliente OkHttp con la configuración de TrustManager personalizado
    private val okHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(
            SSLContext.getInstance("SSL").apply {
                init(null, trustAllCerts, SecureRandom())
            }.socketFactory,
            trustAllCerts[0] as X509TrustManager
        )
        .hostnameVerifier { _, _ -> true }
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // Agregar un interceptor para el registro de solicitudes y respuestas
        .build()

    // Propiedad webService utilizando la delegación by lazy para inicialización perezosa
    val webService: ApiService by lazy {
        // Crear una instancia de Retrofit con la URL base y un convertidor Gson
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL base del servicio web
            .client(okHttpClient) // Usar el cliente OkHttp personalizado
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor Gson
            .build()
            .create(ApiService::class.java) // Crear la instancia de WebService a partir de la interfaz
    }
}
