import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/FriendListPage.css"; // New CSS file
import UserComponent from "../components/UserComponent";
import SearchBar from "../components/SearchBar";
import axios from "axios";
import {filterUsers} from "../utils/filterUsers";
import {handleDelete} from "../axios/UserAxios";

const FriendListPage = () => {
    const user = useSelector((state) => state.user.user);
    const [friends, setFriends] = useState([]);
    const [allFriends, setAllFriends] = useState([]);
    const [searchFriendQuery, setSearchFriendQuery] = useState("");

    const handleDeleteFriend = (id) => {
        setFriends(friends.filter((friend) => friend.id !== id));
    };

    useEffect(() => {
        const fetchFriends = async () => {
            if (user && user.email) {
                try {
                    const response = await axios.get(
                        `http://localhost:8085/friendships/DTO/${user.email}`
                    );
                    if (response.data.length > 0) {
                        setAllFriends(response.data);
                        setFriends(response.data);
                    }
                } catch (error) {
                    console.error("Failed to fetch friends", error);
                }
            }
        };

        fetchFriends();
    }, [user]);

    useEffect(() => {
        if (searchFriendQuery) {
            const filteredFriends = filterUsers(allFriends, searchFriendQuery);
            setFriends(filteredFriends);
        } else {
            setFriends(allFriends);
        }
    }, [searchFriendQuery, allFriends]);

    return (
        <div className="friend-list-page">
            <Header/>
            <div className="content">
                <div className="friend-list-nav">
                    <NavBar/>
                </div>
                <div className="friend-list-content">
                    <h3>Friend list:</h3>
                    <div className="friend-list-search-bar">
                        <SearchBar
                            searchType="friend"
                            setSearchFriendQuery={setSearchFriendQuery}
                            dropdownClassName="friend"
                            searchBarClassName="friend"
                        />
                    </div>

                    {friends.length > 0 ? (
                        friends.map((friend) => (
                            <UserComponent
                                key={friend.email}
                                user={friend}
                                userEmail={user.email}
                                onDelete={handleDeleteFriend}
                                handleDeleteFn={() => handleDelete(user.email, friend.email)}
                            />
                        ))
                    ) : (
                        <div className="no-friends">
                            <p>You have no friends.</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default FriendListPage;
