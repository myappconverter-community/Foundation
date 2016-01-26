package com.myappconverter.java.corefoundations;

/**
 * CFDate objects store dates and times that can be compared to other dates and times. CFDate objects are immutable—there is no mutable
 * counterpart for this opaque type. <br>
 * CFDate provides functions for creating dates, comparing dates, and computing intervals. You use the CFDateCreate function to create
 * CFDate objects. You use the CFDateCompare function to compare two dates, and the CFDateGetTimeIntervalSinceDate function to compute a
 * time interval. Additional functions for managing dates and times are described in Time Utilities Reference <br>
 * CFDate is “toll-free bridged” with its Cocoa Foundation counterpart, NSDate. What this means is that the Core Foundation type is
 * interchangeable in function or method calls with the bridged Foundation object. In other words, in a method where you see an NSDate *
 * parameter, you can pass in a CFDateRef, and in a function where you see a CFDateRef parameter, you can pass in an NSDate instance. This
 * also applies to concrete subclasses of NSDate. See “Interchangeable Data Types” for more information on toll-free bridging.
 */
public class CFDateRef extends CFDate {

}
