import { useState } from "react";
import { useNavigate } from "react-router-dom";

function UserLookup() {
  const [userId, setUserId] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    navigate(`/user-lookup/${userId}`)
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
        <label htmlFor="user-id">User Lookup</label>
        <input type="text" id="user-id" name="user-id"
          onChange={handleInputChange}
          onKeyDown={handleKeyDown}
          placeholder="Enter user Id here"
          required
        />
        <button type="submit">Search</button>
      </form>
    </div>
  )
}
  
export default UserLookup
