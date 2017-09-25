using Homework1.DTO;
using Homework1.Models;
using Microsoft.AspNet.Identity.Owin;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Web.Http;

namespace Homework1.Controllers
{
    public class UsersController : ApiController
    {
        private ApplicationUserManager _userManager;
        public ApplicationUserManager UserManager
        {
            get
            {
                return _userManager ?? Request.GetOwinContext().GetUserManager<ApplicationUserManager>();
            }
            private set
            {
                _userManager = value;
            }
        }

        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Users
        
        public List<UserDTO> GetUsers()
        {
            return UserManager.Users.Select(u => new UserDTO {
                Id = u.Id,
                FirstName=u.FirstName,
                LastName=u.LastName,
                UserName=u.UserName

            }).ToList();
        }
    }
}
