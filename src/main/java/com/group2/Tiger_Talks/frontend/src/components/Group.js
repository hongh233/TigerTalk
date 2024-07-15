import { HiEye, HiEyeOff } from "react-icons/hi";
import "../assets/styles/Group.css";

const Group = ({ group }) => {
	return (
		<div className="group">
			<div className="group-header">
				<div className="group-picture">
					<img src={group.groupImg} />
				</div>
				<div className="group-creator-details">
					<a className="group-link" href={`/group/viewgroup/${group.groupId}`}>
						{group.groupName}{" "}
					</a>
					{group.private ? <HiEyeOff /> : <HiEye />}
					<p>{group.dateCreated}</p>
				</div>
			</div>
			<div className="group-description">
				<p>{group.description}</p>
			</div>
		</div>
	);
};

export default Group;
