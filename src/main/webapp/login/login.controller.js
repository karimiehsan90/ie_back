var module = angular.module('login.controller', []);


//Home Controler
module.controller('loginCtrl', ['$scope', 'Model', function ($scope, Model) {

    $scope.ProjectName = 'اعوان';

    $scope.signIn = function (userName, password) {
        Model.SignIn(userName, password).then(function (data) {
            $scope.message ='خوش آمدید' + userName;
        }, function (error) {
            $scope.message = 'نام کاربری یا رمز عبور اشتباه است';
        }).finally(function () {
        });
    };


}]);



