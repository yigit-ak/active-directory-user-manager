import { useState } from "react";
import { useNavigate } from "react-router-dom";
import GoBack from "@/components/GoBack";

function UserLookup() {
  const [userId, setUserId] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    navigate(`/user-lookup/${userId}`);
    setLoading(false);
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
          value={userId}
          onChange={handleInputChange}
          onKeyDown={handleKeyDown}
          placeholder="Enter user ID here"
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? "Searching..." : "Search"}
        </button>
      </form>
    </div>
  );
}

export default UserLookup;
