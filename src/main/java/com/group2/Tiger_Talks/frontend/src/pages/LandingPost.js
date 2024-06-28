// src/pages/LandingPost.js
import React, {useEffect, useState} from 'react';
import axios from 'axios';

const LandingPage = () => {
    const [posts, setPosts] = useState([]);
    const [recommendations, setRecommendations] = useState([]);
    const [loggedInUserEmail, setLoggedInUserEmail] = useState(null);

    useEffect(() => {
        // Assume the user's email is stored in local storage after login
        const email = localStorage.getItem('userEmail');
        setLoggedInUserEmail(email);

        if (email) {
            axios.get('http://localhost:8085/posts/all')
                .then(response => {
                    setPosts(response.data);
                })
                .catch(error => {
                    console.error('There was an error fetching the posts!', error);
                });

            axios.get(`/recommendations?userEmail=${email}`)
                .then(response => {
                    setRecommendations(response.data);
                })
                .catch(error => {
                    console.error('There was an error fetching the recommendations!', error);
                });

        }
    }, []);

    if (!loggedInUserEmail) {
        return <p>Loading...</p>;
    }

    return (
        <div>
            <h1>Friend's Posts</h1>
            <div>
                {posts.map(post => (
                    <div key={post.postId}>
                        <h2>{post.userEmail}</h2>
                        <p>{post.content}</p>
                        <p>{new Date(post.timestamp).toLocaleString()}</p>
                    </div>
                ))}
            </div>

            <h1>Friend Recommendations</h1>
            <div>
                {recommendations.map(email => (
                    <div key={email}>
                        <p>{email}</p>
                        <button>Add Friend</button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default LandingPage;
