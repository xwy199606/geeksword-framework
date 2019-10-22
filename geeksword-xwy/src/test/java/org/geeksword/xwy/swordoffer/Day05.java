package org.geeksword.xwy.swordoffer;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Day05 {
    public static void main(String[] args) throws UnknownHostException {
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("Local HostAddress: "+addr.getHostAddress());
                    String hostname = addr.getHostName();
            System.out.println("Local host name: "+hostname);
        }
}
