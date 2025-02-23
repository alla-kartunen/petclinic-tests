package objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OwnerInfo {

	String fullName, address, city, telephone;

	public OwnerInfo mapOwner(Owner owner) {
		OwnerInfo ownerInfo = new OwnerInfo();
		ownerInfo.setFullName(owner.getFirstName() + " " + owner.getLastName());
		ownerInfo.setAddress(owner.getAddress());
		ownerInfo.setCity(owner.getCity());
		ownerInfo.setTelephone(owner.getTelephone());
		return ownerInfo;
	}

}
