package smartkuk.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@MappedSuperclass
@SuppressWarnings("serial")
@EqualsAndHashCode(of = "id")
public abstract class StringUuidEntity implements Persistable<String> {

  @Id
  @Column(updatable = false, columnDefinition = "uuid")
  @GenericGenerator(name = "system-gen", strategy = "uuid2")
  @GeneratedValue(generator = "system-gen")
  private @Getter String id;

  @Override
  @JsonIgnore
  public boolean isNew() {
    return id == null;
  }

}
