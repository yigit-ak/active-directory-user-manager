import { useState } from "react";
import { useNavigate } from "react-router-dom";
import GoBack from "@/components/GoBack";
import { getUserByCn } from "@/api.js";

function UserLookup() {
  const [userId, setUserId] = useState('');
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      await getUserByCn(userId); // Check if user exists
      navigate(`/user-lookup/${userId}`);
    } catch (error) {
      console.error(error);
      setMessage("User not found.");
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (event) => {
    setUserId(event.target.value);
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      handleSubmit(event);
    }
  };

  return (
    <div className="user-lookup-page">
      <form id="user-id-form" onSubmit={handleSubmit}>
        <GoBack link='/' />
        <label htmlFor="user-id">User Lookup</label>
        <input
          type="text"
          id="user-id"
          name="user-id"
          onChange={handleInputChange}
          onKeyDown={handleKeyDown}
          placeholder="Enter user Id here"
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? "Searching..." : "Search"}
        </button>
        {message && <div className="info-message">{message}</div>}
      </form>
    </div>
  );
}

export default UserLookup;
