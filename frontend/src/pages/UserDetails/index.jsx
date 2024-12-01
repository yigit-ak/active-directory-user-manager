import GoBack from "../../components/GoBack";

function UserDetails() {
  const client = { // TODO: fetch data from server
    id: 'yigit-ak-123',
    name: 'Yigit',
    surname: 'Ak',
    group: 'Interns',
    email: 'yigit-ak@yigitak.com',
    phone: '+1 (123) 456-7890',
    locked: true,
  };

  return (
    <div className="user-details-page">
      <div className="user-info-card">
        <GoBack link={'/user-lookup'} />

        <div className="info-card-layout-top">
          <h1 className="info-card-title">{client.id}</h1>
          {client.locked && <div className="user-locked-warning">User is locked.</div>}
        </div>

        <div className="info-card-layout-bottom">
          <div className="info-card-body">
            <div className="user-info">
              <span className="info-card-attribute">Name: </span> {client.name}
            </div>
            <div className="user-info">
              <span className="info-card-attribute">Surname: </span> {client.surname}
            </div>
            <div className="user-info">
              <span className="info-card-attribute">Group: </span> {client.group}
            </div>
            <div className="user-info">
              <span className="info-card-attribute">Email: </span> {client.email}
            </div>
            <div className="user-info">
              <span className="info-card-attribute">Phone: </span> {client.phone}
            </div>
          </div>
        
          <div className="user-info-card-buttons">
            {client.locked ? <button>Unlock User</button> : <button>Lock User</button>}
            <button>Reset Password</button>
          </div>

        </div>
      </div>
    </div>
  )
}
    
  export default UserDetails
  