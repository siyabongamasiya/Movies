package media.project.movies.Domain.UseCases

import android.content.Context
import com.google.gson.Gson
import media.project.movies.Constants.Constants
import media.project.movies.Domain.Model.Response
import javax.inject.Inject

class GetLocalMovieById @Inject constructor() {

    operator fun invoke(id : Int,context: Context) : Response{
        //get movie object with id that was saved in preference from remote
        val sharedPreference = context.getSharedPreferences(Constants.StorageName.con,Context.MODE_PRIVATE)
        val json = sharedPreference.getString(Constants.KeyName.con,"Null")

        val response = Gson().fromJson(json,Response :: class.java)

        return response
    }
}