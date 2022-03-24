package com.example.tnw_driver_navigation

data class TaskApiConstants(
    var taskName: String? = null,
    var taskId: Int? = null,
    var taskTimeSlot: String? = null,
    var taskStartDelivery: String? = null,
    var taskEndDelivery: String? = null,
    var taskPhone: String? = null,
    var taskType: String? = null,
    var taskHaveAddOn: String? = null,
    var taskIsPaid: String? = null,
    var taskDestinationLong: Int? = null,
    var taskDestinationLat: Int? = null
)
