// +package com.mountainframework.config.spring;
//
// import org.springframework.beans.factory.DisposableBean;
// import org.springframework.beans.factory.InitializingBean;
// import
// org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
// public class RpcRegistery implements InitializingBean, DisposableBean {
// private String ipAddr;
// private String protocol;
// private String echoApiPort;
// private AnnotationConfigApplicationContext context = new
// AnnotationConfigApplicationContext();
//
// @Override
// public void destroy() throws Exception {
// // TODO closed
// // RpcServerExecutor.getInstance().stop();
// }
//
// @Override
// public void afterPropertiesSet() throws Exception {
// }
//
// public String getIpAddr() {
// return ipAddr;
// }
//
// public void setIpAddr(String ipAddr) {
// this.ipAddr = ipAddr;
// }
//
// public String getProtocol() {
// return protocol;
// }
//
// public void setProtocol(String protocol) {
// this.protocol = protocol;
// }
//
// public String getEchoApiPort() {
// return echoApiPort;
// }
//
// public void setEchoApiPort(String echoApiPort) {
// this.echoApiPort = echoApiPort;
// }
// }
