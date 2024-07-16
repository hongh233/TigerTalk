import {
	FaLock,
	FaUnlock,
	FaCog,
	FaSignOutAlt,
	FaSignInAlt,
} from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import "../assets/styles/Group.css";
import { useSelector } from "react-redux";
import { handleJoinGroup, handleLeaveGroup } from "./../axios/GroupAxios";
const Group = ({ group }) => {
	const email = useSelector((state) => state.user.user.email);
	const member = group.groupMemberList.find(
		(mem) => mem.userProfileDTO.email === email
	);
	const isOwner = group.groupCreatorEmail === email;
	const isMember = Boolean(member);
	const groupMembershipId = member ? member.groupMembershipId : null;
	const navigate = useNavigate();

	const handleActionClick = async () => {
		let action = "";
		if (isMember) {
			action = "leave";
		} else {
			action = "join";
		}

		if (
			!isOwner &&
			window.confirm(`Are you sure you want to ${action} this group?`)
		) {
			if (isMember) {
				await handleLeaveGroup(groupMembershipId);
				window.alert("Leave group successfully!");
				window.location.reload();
			} else {
				await handleJoinGroup(email, group.groupId);
				window.alert("Joined group successfully!");
				navigate(`/group/viewgroup/${group.groupId}`);
			}
		} else {
			if (isOwner) {
				navigate(`/group/${group.groupId}/setting`);
			}
		}
	};

	return (
		<div className="group">
			<div className="group-header">
				<div
					className="group-creator-details"
					onClick={() => {
						navigate(`/group/viewgroup/${group.groupId}`);
					}}
				>
					{group.groupName} {group.private ? <FaLock /> : <FaUnlock />}
					<p>{group.dateCreated}</p>
				</div>

				<div
					className="one-group-background-image"
					onClick={() => {
						navigate(`/group/viewgroup/${group.groupId}`);
					}}
				>
					<img src={group.groupImg} alt="Group cover" />
				</div>
				<div
					className={`group-action ${
						isOwner ? "settings" : isMember ? "leave" : "join"
					}`}
					onClick={handleActionClick}
				>
					<div className="group-action-text">
						{isOwner ? (
							<FaCog />
						) : isMember ? (
							<FaSignOutAlt />
						) : (
							<FaSignInAlt />
						)}
					</div>
				</div>
			</div>
		</div>
	);
};

export default Group;
