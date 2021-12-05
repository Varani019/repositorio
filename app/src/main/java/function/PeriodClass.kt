package function
// calcula  a idade
// fun ´para verifica se a data nao e inventada  ex: 35/13/2080

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period

class PeriodClass {

    @RequiresApi(Build.VERSION_CODES.O)
    fun period(str: String): Period? {
        val now : LocalDate = LocalDate.now() //pega a data to celular
        val yaer = str.split("/")
        var data: LocalDate? = null
        try{
            data= LocalDate.of(yaer[2].toInt(),yaer[1].toInt(),yaer[0].toInt())
        }catch (e:Exception){
            Log.i("ERRO de data",e.toString())
            return null
        }
        val period: Period = Period.between(data,now)
        return period
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkPeriod(str: String) : Boolean? {
        return period(str) != null && period(str)!! .days >= 0

    }
}