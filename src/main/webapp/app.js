angular.module('avan', [
        'ngRoute',
        'login.controller',
        'login.service',
 ])

    .config(['$routeProvider', function ($routeProvider) {

           $routeProvider.
                when("/login", { templateUrl: "login/login.view.html", controller: "loginCtrl" }).
                otherwise({ redirectTo: '/login' });

       }
    ])

  