package com.aceteam.tm.rest.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * @description: IP analysis
 * @author: haoran
 */

@Slf4j
public class IpUtil {

    /**
     * Get visitor ip
     *
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        /**
         * X-Forwarded-For: abbreviated XFF header, which represents the real IP address of the client, i.e., the requesting side of HTTP. This item is only added if it passes through an HTTP proxy or load-balanced server.
         * * The standard format is as follows: X-Forwarded-For: client_ip, proxy1_ip, proxy2_ip
         * * This header is constructible, so some applications should validate the obtained ip.
         */
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        /**
         * In a multilevel proxy network, using getHeader("x-forwarded-for") directly may get unknown information
         * In this case, you need to get the HTTP header information which is repackaged by the proxy server.
         */
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        return ip;
    }

    /**
     * Get the visitor's operating system
     *
     * @param request
     * @return
     */
    public static String getOS(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        log.info("User-Agent: {}", agent);
        StringTokenizer st = new StringTokenizer(agent, ";");
        st.nextToken();
        // Get the name of the visitor's operating system
        String os = st.nextToken();

        // Optimize operating system name win
        boolean isWin2K = agent.contains("Windows NT 5.0") || agent.contains("Windows 2000");
        if (isWin2K) {
            os = "Windows 2000";
        }
        boolean isWinXP = agent.contains("Windows NT 5.1") || agent.contains("Windows XP");
        if (isWinXP) {
            os = "Windows XP";
        }
        boolean isWin2003 = agent.contains("Windows NT 5.2") || agent.contains("Windows 2003");
        if (isWin2003) {
            os = "Windows 2003";
        }
        boolean isWinVista = agent.contains("Windows NT 6.0") || agent.contains("Windows Vista");
        if (isWinVista) {
            os = "Windows Vista";
        }
        boolean isWin7 = agent.contains("Windows NT 6.1") || agent.contains("Windows 7");
        if (isWin7) {
            os = "Windows 7";
        }
        boolean isWin8 = agent.contains("Windows NT 6.2") || agent.contains("Windows NT 6.3") || agent.contains("Windows 8");
        if (isWin8) {
            os = "Windows 8";
        }
        boolean isWin10 = agent.contains("Windows NT 10") || agent.contains("Windows 10");
        if (isWin10) {
            os = "Windows 10";
        }
        // mac
        boolean mac = agent.contains("Mac OS X");
        if (mac) {
            os = "Mac OS X";
        }
        // linux
        boolean linux = agent.contains("Linux x86_64");
        if (linux) {
            os = "Linux x86_64";
        }
        // Android 5.0) AppleWebKit
        boolean android = agent.contains("Android 5.0) AppleWebKit");
        if (android) {
            os = "Android 5.0";
        }

        // Special treatment of U
        if ("U".equalsIgnoreCase(os.trim())) {
            os = st.nextToken();
        }

        return os;
    }

}

