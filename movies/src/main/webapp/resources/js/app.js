var app = angular.module("moviesApp", []);

app.controller("moviesCtrl", function($scope, $http) {
	
	$scope.loggedUsers = [];
	$scope.showEditButtons = false;

	$scope.toggle = function() {
		$scope.showEditButtons = !($scope.showEditButtons);
	}

	$scope.getLoggedUsers = function() {
		$http.get("loggedUsers").success(function(data) {
			$scope.loggedUsers = data;
		});
	}

});