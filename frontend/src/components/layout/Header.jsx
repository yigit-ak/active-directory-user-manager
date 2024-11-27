import companyLogo from '@/assets/company-logo.png'

function Header () {
  return (
    <header>
      <img className='header-logo' src={companyLogo} alt="company logo" />
    </header>
  )
}

export default Header
