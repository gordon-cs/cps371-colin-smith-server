/* This simple app has only one view, and so only one controller.
 * Its job is to provide data (from the weatherData service) for display
 * by the html page (index.html).
 */
weatherApp.controller('MainCtrl',
    function($scope, $state, weatherData, LocationStore) {
        //read default settings into scope
        console.log('inside home');
        $scope.city = LocationStore.city;
        var latitude = LocationStore.latitude;
        var longitude = LocationStore.longitude;

        //call getCurrentWeather method in factory
        var weatherInit = function(lat, lng) {
            weatherData.getCurrentWeather(lat, lng).then(function() {
                $scope.tempCurrent = weatherData.tempNow();
                $scope.tempTomorrowHigh = weatherData.tempTomorrowHigh();
                $scope.tempTonightLow = weatherData.tempToMidnightLow();
            });
        };

        weatherData.getLocation() // getLocation returns the position obj
            .then(function(position) {
                weatherInit(position.latitude, position.longitude);
            }, function(err) {
                console.log(err);
                weatherInit(latitude, longitude);
            });

    });
