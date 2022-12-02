package com.example.semestralnezadanie.database.users

import android.util.Log
import com.example.semestralnezadanie.api.ApiRest
import com.example.semestralnezadanie.api.UserGeneralResponse
import com.example.semestralnezadanie.api.UserLoginRequest
import com.example.semestralnezadanie.api.UserRequest
import java.io.IOException
import java.lang.Exception

class UserDataRepository (private val apiService : ApiRest)
{

    companion object{
        @Volatile
        private var INSTANCE: UserDataRepository? = null

        fun getInstance(apiService : ApiRest) : UserDataRepository =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserDataRepository(apiService).also { INSTANCE = it }
            }
    }

    suspend fun registerUser(userName : String, userPassword : String, errorOut : (error : String) -> Unit, status : (isSuccess : UserGeneralResponse?) -> Unit)
    {
        try{
            var response = apiService.createUser(UserRequest(userName = userName, userPassword = userPassword))
            if(response.isSuccessful)
            {
                response.body()?.let { userResponse ->
                    if(userResponse.userId == "-1")
                    {
                        status(null)
                        errorOut("Používateľské meno už existuje")
                    }
                    else
                    {
                        status(userResponse)
                    }
                }
            }
            else if(response.code() == 400)
            {
                status(null)
                errorOut("Registrácia zlyhala, nesprávny request")
            }
            else if(response.code() == 401)
            {
                status(null)
                errorOut("Registrácia zlyhala, neautorizovaný request")
            }
            else if(response.code() == 404)
            {
                status(null)
                errorOut("Registrácia zlyhala, endpoint neexistuje")
            }
            else if(response.code() == 500)
            {
                status(null)
                errorOut("Registrácia zlyhala, chyba v databáze")
            }
        }
        catch (e0: IOException)
        {
            e0.printStackTrace()
            status(null)
            errorOut("Registrácia zlyhala, skontrolujte internetové pripojenie")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            status(null)
            errorOut("Registrácia zlyhala")
        }

    }

    suspend fun loginUser(userName: String, userPassword: String, errorOut : (error : String) -> Unit, status : (isSuccess : UserGeneralResponse?) -> Unit)
    {
        try {
            var response = apiService.userLogin(UserLoginRequest(userName = userName, userPassword = userPassword))

            if(response.isSuccessful)
            {
                response.body()?.let { userResponse ->
                    if(userResponse.userId == "-1")
                    {
                        status(null)
                        errorOut("Prihlásenie zlyhalo, nesprávne meno alebo heslo")
                    }
                    else
                    {
                        status(userResponse)
                    }
                }
            }
            else if(response.code() == 400)
            {
                status(null)
                errorOut("Prihlásenie zlyhalo, nesprávny request")
            }
            else if(response.code() == 401)
            {
                status(null)
                errorOut("Prihlásenie zlyhalo, neautorizovaný request")
            }
            else if(response.code() == 404)
            {
                status(null)
                errorOut("Prihlásenie zlyhalo, endpoint neexistuje")
            }
            else if(response.code() == 500)
            {
                status(null)
                errorOut("Prihlásenie zlyhalo, chyba v databáze")
            }
        }
        catch (e0 : Exception)
        {
            e0.printStackTrace()
            errorOut("Prihlásenie zlyhalo, skontrolujte si internetové pripojenie")
            status(null)
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            errorOut("Prihlásenie zlyhalo, neočakávaná chyba")
            status(null)
        }
    }

}