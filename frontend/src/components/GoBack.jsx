import { useNavigate  } from 'react-router-dom'

function GoBack({link}) {
  const navigate = useNavigate();

  const handleClick = () => {
      navigate(link);  // Navigate to the desired path
  };

  return (
    <>
      <div className="go-back" onClick={handleClick}>
        Go Back
      </div>
    </>
  );
    
}

export default GoBack
