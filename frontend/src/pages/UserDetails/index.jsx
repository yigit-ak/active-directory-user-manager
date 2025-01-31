import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import GoBack from "../../components/GoBack";
import {
  fetchUser,
  enableUserAccount,
  disableUserAccount,
  resetUserPassword
} from "../../api.js"

function UserDetails() {
  const { commonName } = useParams();
  const [client, setClient] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const getUserData = async () => {
      try {
        const user = await fetchUser(commonName);
        setClient(user);
      } catch (error) {
        console.error("Failed to fetch user:", error);
      } finally {
        setLoading(false);
      }
    };

    getUserData();
  }, [commonName]);

  const handleToggleLock = async () => {
    if (!client) return;

    try {
      if (client.isEnabled) {
        await disableUserAccount(client.commonName);
      } else {
        await enableUserAccount(client.commonName);
      }

      // Refetch user data to update UI
      const updatedUser = await fetchUser(commonName);
      setClient(updatedUser);
    } catch (error) {
      console.error("Failed to toggle user lock:", error);
    }
  };

  const handleResetPassword = async () => {
    if (!client) return;

    try {
      await resetUserPassword(client.commonName);
      alert("User password has been reset.");
    } catch (error) {
      console.error("Failed to reset password:", error);
    }
  };

  if (loading) {
    return <div>Loading user details...</div>;
  }

  if (!client) {
    return <div>User not found.</div>;
  }

  return (
    <div className="user-details-page">
      <div className="user-info-card">
        <GoBack link={'/user-lookup'} />

        <div className="info-card-layout-top">
          <h1 className="info-card-title">{client.commonName}</h1>
          {client.isEnabled && <div className="user-locked-warning">User is locked.</div>}
        </div>

        <div className="info-card-layout-bottom">
          <div className="info-card-body">
            <div className="user-info">
              <span className="info-card-attribute">Name: </span> {client.firstName}
            </div>
            <div className="user-info">
              <span className="info-card-attribute">Surname: </span> {client.lastName}
            </div>
            <div className="user-info">
              <span className="info-card-attribute">Vendor: </span> {client.vendor}
            </div>
            <div className="user-info">
              <span className="info-card-attribute">Email: </span> {client.email}
            </div>
            <div className="user-info">
              <span className="info-card-attribute">Phone: </span> {client.phoneNumber}
            </div>
          </div>
        
          <div className="user-info-card-buttons">
            <button onClick={handleToggleLock}>
              {!client.isEnabled ? "Lock User" : "Unlock User"}
            </button>
            <button onClick={handleResetPassword}>Reset Password</button>
          </div>

        </div>
      </div>
    </div>
  )
}
    
  export default UserDetails;
  