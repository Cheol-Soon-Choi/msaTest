package com.ccs.event;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

//커스텀 인풋, 아웃풋 채널을 추가
public interface CustomChannels {
    @Input("inboundOrgChanges")
    SubscribableChannel orgs();

//    @Output("outboundOrg")
//    MessageChannel outnoutndOrg();
}
