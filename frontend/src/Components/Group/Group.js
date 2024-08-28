import React from "react";
import "../../assets/styles/Components/Group/Group.css";
import { FaLock, FaLockOpen } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";


const Group = ({ group }) => {
	const email = useSelector((state) => state.user.user.email);
	const member = group.groupMemberList.find(
		(mem) => mem.userProfileDTO.email === email
	);
	const navigate = useNavigate();

	return (
		<div className="group">
			<div className="group-header"  onClick={() => {navigate(`/group/viewgroup/${group.groupId}`);}}>

				<div className="one-group-background-image">
					<img src={group.groupImg} alt="Group cover" />
				</div>

				<div className="group-creator-details">
					{group.groupName} {group.isPrivate ? <FaLock /> : <FaLockOpen />}
					<p>{group.dateCreated}</p>{/*TODO: wait to see what to do here*/}
				</div>

			</div>
		</div>
	);
};

export default Group;
