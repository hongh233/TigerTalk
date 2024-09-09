import React, {useEffect, useState} from "react";
import "../../assets/styles/Pages/Search/SearchPage.css";
import {useLocation, useNavigate} from "react-router-dom";
import SearchFriendsResult from "../../Components/Search/SearchFriendsResult";
import { searchGroups, searchUsers } from "../../axios/Search/SearchAxios";
import SearchGroupsResult from "../../Components/Search/SearchGroupsResult";


const SearchPage = () => {
	const location = useLocation();
	const [searchContent, setSearchContent] = useState(location.state?.content || "");
	const [users, setUsers] = useState([]);
	const [groups, setGroups] = useState([]);
	const navigate = useNavigate();


	useEffect(() => {
		if (location.state?.content) {
			setSearchContent(location.state.content);
		}
	}, [location.state]);


	useEffect(() => {
		if (searchContent) {
			searchUsers(searchContent)
				.then((users) => setUsers(users))
				.catch((error) => console.error("Error fetching users:", error));

			searchGroups(searchContent)
				.then((groups) => setGroups(groups))
				.catch((error) => console.error("Error fetching groups:", error));

		} else {
			setUsers([]);
			setGroups([]);
		}
	}, [searchContent]);

	return (
		<div className="main-page">
			<div className="content">

				<div className="search-content-container">

					<div>
						<h2>Users matched with keyword:</h2>
						<div className="search-content-section-friend">
							{users && users.length > 0 ? (
								<SearchFriendsResult
									searchedUsers={users}
								/>
							) : (
								<p>No users found with this keyword</p>
							)}
						</div>
					</div>

					<div>
						<h2>Groups matched with keyword:</h2>
						<div className="search-content-section-group">
							{groups && groups.length > 0 ? (
								<SearchGroupsResult groups={groups}/>
							) : (
								<p>No groups found with this keyword</p>
							)}
						</div>
					</div>

				</div>
			</div>
		</div>
	);
};

export default SearchPage;
