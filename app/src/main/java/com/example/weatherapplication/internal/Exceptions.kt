package com.example.weatherapplication.internal

import java.io.IOException
import java.lang.IllegalStateException


class NoConnectivityException: IOException()
class LocationPermissionNotGrantedException: Exception()
class DateNotFoundException: Exception()
class BindDataException: IllegalStateException()

