import React from "react";
import "../../assets/styles/Components/Search/SearchFriendsResult.css";
import StatusIcon from "../Main/StatusIcon";
import {useNavigate} from "react-router-dom";


const SearchFriendsResult = ({ users }) => {

	const navigate = useNavigate();

	return (
		<div className="search-results-dropdown-header">
			{users.map((user) => (
				<div className="search-result-item"
					 key={user.email}
					 onClick={(email) => {navigate(`/profile/${user.email}`)}}
				>

						<div className="search-friend-profile-header">

							<div className="common-profile-picture-and-status-icon">
								<img src={user.profilePictureUrl} alt="Profile" />
								<StatusIcon status={user.onlineStatus} />
							</div>

							<div className="search-friend-profile-info">
								<div className="search-friend-profile-name-status">
									<div id="userName">{user.userName}</div>
									<div id="email">{user.email}</div>
								</div>
							</div>

						</div>
				</div>
			))}
		</div>
	);
};

export default SearchFriendsResult;
