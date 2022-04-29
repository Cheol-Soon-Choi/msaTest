package com.ccs.event.soruce;

import com.ccs.event.models.OrganizationChangeModel;
import com.ccs.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component // 메시지 발행 코드
public class SimpleSourceBean {
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    @Autowired
    public SimpleSourceBean(Source source) {
        this.source = source;
    }

    public void publishOrgChange(String action, String orgId) {
        logger.debug("Sending Kafka message {} for Organization Id: {}", action, orgId);

        //POJO
        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                orgId,
                UserContext.getCorrelationId());

        //source클래스를 사용해서 메시지 전달 -> 단일채널에 메시지 전달시 사용
        source.output().send(MessageBuilder.withPayload(change).build());
    }
}
