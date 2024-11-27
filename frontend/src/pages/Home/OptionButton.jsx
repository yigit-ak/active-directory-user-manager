import { useNavigate  } from 'react-router-dom'

function OptionButton ({ link, title }) {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(link);  // Navigate to the desired path
  };

  return (
    <>
      <div className="option-button" onClick={handleClick}>
        {title}
      </div>
    </>
  )
}

export default OptionButton
