package media.project.movies.Domain.UseCases

import android.content.Context
import com.google.gson.Gson
import media.project.movies.Constants.Constants
import media.project.movies.Domain.Model.Response
import javax.inject.Inject

class SaveResponse @Inject constructor(){
    operator fun invoke(response: Response,context: Context){
        //save the response from remote in order to access from another screen using id
        val gson  = Gson()
        val sharedPreference = context.getSharedPreferences(Constants.StorageName.con, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(Constants.KeyName.con,gson.toJson(response))
        editor.apply()
    }
}