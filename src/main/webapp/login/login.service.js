 
angular.module('login.service', [])
 .factory('Model', ['$http',
    function ($http) {
        var Model = {};

        //SignIn
        Model.SignIn = function (email, password) {
            return $http({
                url: '/infra/core/user/login',
                method: "POST",
                data: {
                    email: email,
                    password: password
                }
            }).then(function (result) { return result.data; });
        };
 
         return Model;
    }]);






























