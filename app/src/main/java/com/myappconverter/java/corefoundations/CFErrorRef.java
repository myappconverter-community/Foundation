package com.myappconverter.java.corefoundations;


/**
 * A CFError object encapsulates more rich and extensible error information than is possible using only an error code or error string. The
 * core attributes of a CFError object are an error domain (represented by a string), a domain-specific error code, and a “user info”
 * dictionary containing application-specific information. Errors are required to have a domain and an error code within that domain.
 * Several well-known domains are defined corresponding to Mach, POSIX, and OSStatus errors. The optional “user info” dictionary may provide
 * additional information that might be useful for the interpretation and reporting of the error, including a human-readable description for
 * the error. The “user info” dictionary sometimes includes another CFError object that represents an error in a subsystem underlying the
 * error represented by the containing CFError object. This underlying error object may provide more specific information about the cause of
 * the error. In general, a method should signal an error condition by returning, for example, false or NULL rather than by the simple
 * presence of an error object. The method can then optionally return an CFError object by reference, in order to further describe the
 * error. CFError is toll-free bridged to NSError in the Foundation framework—for more details on toll-free bridging, see “Toll-Free Bridged
 * Types”. NSError has some additional guidelines that make it easy to report errors automatically to users and attempt to recover from
 * them. See Error Handling Programming Guide for more information on NSError programming guidelines.
 */
public class CFErrorRef extends CFTypeRef {

    private int code;
    private CFStringRef domain;

    public long getCode() {
        return code;
    }

    public CFStringRef getDomain() {
        return domain;
    }
}
