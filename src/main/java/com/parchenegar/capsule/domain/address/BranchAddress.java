package com.parchenegar.capsule.domain.address;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.parchenegar.capsule.domain.base.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("branch")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class BranchAddress extends Address
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID")
    @PrimaryKeyJoinColumn(name = "ENTITY_ID", referencedColumnName = "ID")
    Branch branch;
}
