package com.example.semestralnezadanie.database.users

import android.util.Log
import android.widget.Toast
import com.example.semestralnezadanie.api.ApiRest
import com.example.semestralnezadanie.api.UserLoginRequest
import com.example.semestralnezadanie.api.UserRequest
import com.example.semestralnezadanie.database.LocalCache
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.coroutineContext

class UserDataRepository private constructor(private val apiService : ApiRest)
{

    companion object{
        @Volatile
        private var INSTANCE: UserDataRepository? = null

        fun getInstance(apiService : ApiRest) : UserDataRepository =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserDataRepository(apiService).also { INSTANCE = it }
            }
    }

    suspend fun registerUser(userName : String, userPassword : String)
    {
        try{
            var response = apiService.createUser(UserRequest(userName = userName, userPassword = userPassword))
            if(response.isSuccessful)
            {
                response.body()?.let { userResponse ->
                    if(userResponse.userId == "-1")
                    {
                        Log.e("Error name","Používateľské meno už existuje")
                    }
                    else
                    {
                        Log.d("Not error", "Success")
                    }
                }
            }
            else
            {
                Log.e("Error registration","Registrácia zlyhala")
            }
        }
        catch (e0: IOException)
        {
            e0.printStackTrace()
            Log.d("Network error", "Check internet connection")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            Log.d("Error registration", "Registrácia zlyhala")
        }

    }

    suspend fun loginUser(userName: String, userPassword: String)
    {
        try {
            var response = apiService.userLogin(UserLoginRequest(userName = userName, userPassword = userPassword))

            if(response.isSuccessful)
            {
                response.body()?.let { userResponse ->
                    if(userResponse.userId == "-1")
                    {
                        Log.e("Bad login", "Wrong username or password")
                    }
                    else
                    {
                        Log.d("Correct login", "Success")
                    }
                }
            }
            else
            {
                Log.e("Login failed", "Login failed, please try again later")
            }
        }
        catch (e0 : Exception)
        {
            e0.printStackTrace()
            Log.d("Network error", "Check internet connection")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            Log.d("Bad login", "Failed to login, unexpected error")
        }
    }

}