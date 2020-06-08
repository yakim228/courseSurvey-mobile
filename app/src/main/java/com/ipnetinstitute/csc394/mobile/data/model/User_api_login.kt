package com.ipnetinstitute.csc394.mobile.data.model

class User_api_login(
    var id: Int,
    var username: String,
    var email: String,
    var roles: Array<Role>,
    var accessToken: String,
    var tokenType: String
) {
    class Role(var id: Int, var name: String) {

    }
}