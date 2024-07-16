import { HiEye, HiEyeOff } from "react-icons/hi";
import "../assets/styles/Group.css";

const Group = ({ group }) => {
	console.log(group);
	return (
		<div className="group">
			<a className="group-link" href={`/group/viewgroup/${group.groupId}`}>
				<div className="group-header">
					<div className="group-creator-details">
						{group.groupName} {group.private ? <HiEyeOff /> : <HiEye />}
						<p>{group.dateCreated}</p>
					</div>
					<div className="group-background-image">
						<img src={group.groupImg} />
					</div>
				</div>
				<div className="group-description">
					<p>{group.description}</p>
				</div>
			</a>
		</div>
	);
};

export default Group;
