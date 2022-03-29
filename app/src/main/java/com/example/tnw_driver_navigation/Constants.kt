package com.example.tnw_driver_navigation

import android.content.Context


object Constants {

    var logoutButtonIsClicked = false;

    var driverId = ""

    var login =
        "https://www.emeraldsoft.uk/projects/hamza/tnw_retail_calculator_1_2_5/web_services/mobile/login.php"
    var deviceToken =
        "https://www.emeraldsoft.uk/projects/hamza/tnw_retail_calculator_1_2_5/web_services/mobile/deviceToken.php"
    var taskList =
        "https://www.emeraldsoft.uk/projects/hamza/tnw_retail_calculator_1_2_5/web_services/mobile/getDriverTasks.php"
    var startDelivery =
        "https://www.emeraldsoft.uk/projects/hamza/tnw_retail_calculator_1_2_5/web_services/mobile/startDelivery.php";
    var endDelivery =
        "https://www.emeraldsoft.uk/projects/hamza/tnw_retail_calculator_1_2_5/web_services/mobile/endDelivery.php";
    var trackDriver =
        "https://www.emeraldsoft.uk/projects/hamza/tnw_retail_calculator_1_2_5/web_services/mobile/trackDriver.php";

    var getDrivers =
        "https://www.emeraldsoft.uk/projects/hamza/tnw_retail_calculator_1_2_5/web_services/mobile/getDrivers.php";

    var nameForCheckingAdmin = ""
    var ifAdminLogin = false
    var driverSelectedByAdmin = ""
    var driverIdSelectedByAdmin = 0

    var destinationLong = ""
    var destinationLat = ""

}