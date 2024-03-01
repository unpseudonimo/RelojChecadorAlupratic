import com.example.relojchecadoralupratic.core.Constantes
import com.example.relojchecadoralupratic.network.ApiService
import com.google.gson.GsonBuilder
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
    val webService: ApiService by lazy {
        // Crear una instancia de Retrofit con la URL base y un convertidor Gson
        Retrofit.Builder()
            .baseUrl(Constantes.API_URL) // URL base del servicio web
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) // Convertidor Gson
            .build()
            .create(ApiService::class.java) // Crear la instancia de WebService a partir de la interfaz
    }
}
