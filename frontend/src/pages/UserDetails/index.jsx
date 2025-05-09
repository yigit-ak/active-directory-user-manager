import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import GoBack from "@/components/GoBack";
import {
  getUserByCn,
  lockUser,
  unlockUser,
  resetPassword,
} from "@/api.js";

function UserDetails() {
  const { cn } = useParams();
  const [user, setUser] = useState(null);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(true);
  const [actionLoading, setActionLoading] = useState(false);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const data = await getUserByCn(cn);
        setUser(data);
      } catch (error) {
        console.error(error);
        setMessage(error.message || "Failed to load user data.");
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, [cn]);

  const handleToggleLock = async () => {
    if (!user) return;
    setActionLoading(true);
    setMessage("");

    try {
      if (user.isEnabled) {
        await lockUser(user.commonName);
        setUser({ ...user, isEnabled: false });
        setMessage("User locked.");
      } else {
        await unlockUser(user.commonName);
        setUser({ ...user, isEnabled: true });
        setMessage("User unlocked.");
      }
    } catch (error) {
      console.error(error);
      setMessage(error.message || "Failed to toggle lock.");
    } finally {
      setActionLoading(false);
    }
  };

  const handleResetPassword = async () => {
    if (!user) return;
    setActionLoading(true);
    setMessage("");

    try {
      await resetPassword(user.commonName);
      setMessage("Password has been reset and sent to the user's email.");
    } catch (error) {
      console.error(error);
      setMessage(error.message || "Failed to reset password.");
    } finally {
      setActionLoading(false);
    }
  };

  if (loading) return <div>Loading user details...</div>;

  if (!user) return <div>{message || "User not found."}</div>;

  return (
    <div className="user-details-page">
      <div className="user-info-card">
        <GoBack link={'/user-lookup'} />

        <div className="info-card-layout-top">
          <h1 className="info-card-title">{user.commonName}</h1>
          {!user.isEnabled && <div className="user-locked-warning">User is locked.</div>}
        </div>

        <div className="info-card-layout-bottom">
          <div className="info-card-body">
            <div className="user-info"><span className="info-card-attribute">Name: </span>{user.firstName}</div>
            <div className="user-info"><span className="info-card-attribute">Surname: </span>{user.lastName}</div>
            <div className="user-info"><span className="info-card-attribute">Group: </span>{user.vendor}</div>
            <div className="user-info"><span className="info-card-attribute">Email: </span>{user.email}</div>
            <div className="user-info"><span className="info-card-attribute">Phone: </span>{user.phoneNumber}</div>
          </div>

          <div className="user-info-card-buttons">
            <button onClick={handleToggleLock} disabled={actionLoading}>
              {user.isEnabled ? "Lock User" : "Unlock User"}
            </button>
            <button onClick={handleResetPassword} disabled={actionLoading}>
              Reset Password
            </button>
          </div>

          {message && <div className="info-message">{message}</div>}
        </div>
      </div>
    </div>
  );
}

export default UserDetails;
