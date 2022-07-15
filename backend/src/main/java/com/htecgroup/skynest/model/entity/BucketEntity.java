package com.htecgroup.skynest.model.entity;

import com.htecgroup.skynest.lambda.LambdaType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "bucket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BucketEntity extends ObjectEntity {

  private static final long serialVersionUID = 7020161097724572834L;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity company;

  private String description;

  @Column(insertable = false)
  private long size;

  @Column(name = "public")
  @With
  private Boolean isPublic;

  @ElementCollection(targetClass = LambdaType.class)
  @CollectionTable(name = "lambda_on_bucket", joinColumns = @JoinColumn(name = "bucket_id"))
  @Column(name = "lambda_name")
  private Set<LambdaType> lambdaTypes;

  public void addLambda(LambdaType lambdaType) {
    this.getLambdaTypes().add(lambdaType);
  }
}
