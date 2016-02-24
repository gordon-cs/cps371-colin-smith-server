'use strict';

// 'weather' is referenced in index.html, 2nd arg is dependencies
var weatherApp = angular.module('weather', ['ionic', 'ngResource']);

var forecastioWeather = ['$q', '$resource', '$http', 'FORECASTIO_KEY', 
    function($q, $resource, $http, FORECASTIO_KEY) {
	var url = 'https://api.forecast.io/forecast/' + FORECASTIO_KEY + '/';

	return {
	    getCurrentWeather: function(lat, lng) {
		return $http.jsonp(url + lat + ',' + lng +
				   '?callback=JSON_CALLBACK');
	    }
	}
    }
];

weatherApp.constant('FORECASTIO_KEY', '14e723fbe931ee119ade496aabcf28ba');

weatherApp.controller('MainCtrl',
    function($scope,$state,WeatherData) {
	var latitude  = 42.589611;
	var longitude = -70.819806;
    
	//call getCurrentWeather method in factory
	WeatherData.getCurrentWeather(latitude,longitude).then(function(resp) {
	    $scope.current = resp.data;
	    console.log('GOT CURRENT', $scope.current);
	}, function(error) {
	    alert('Unable to get current conditions');
	    console.error(error);
	});
    }
);
    
weatherApp.factory('WeatherData', forecastioWeather);


