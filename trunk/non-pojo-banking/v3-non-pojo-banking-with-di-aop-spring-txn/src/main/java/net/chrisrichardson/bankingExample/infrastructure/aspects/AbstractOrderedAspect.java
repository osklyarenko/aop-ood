package net.chrisrichardson.bankingExample.infrastructure.aspects;

import org.springframework.core.Ordered;

public abstract class AbstractOrderedAspect implements Ordered {

  private Integer order;

  public final int getOrder() {
    return order;
  }
  
  public void setOrder(int order) {
    this.order = order;
  }

}
