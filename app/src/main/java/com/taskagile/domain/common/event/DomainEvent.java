package com.taskagile.domain.common.event;

import org.springframework.context.ApplicationEvent;

public abstract class DomainEvent extends ApplicationEvent {

  private static final long serialVersionUID = -444783093811334147L;

  public DomainEvent(Object source){
    super(source);
  }

  public long occuredAt(){
    //잠재적인 구현체의 타임스탬프를 반환한다
    return getTimestamp();
  }
}
