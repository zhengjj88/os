export interface User {
  id: string;
  username: string;
  password: string;
  realname: string;
  createdate: Date;
  imsi: string;
  dept: string;
  enable: string;
}

export function addUser(username: string, callback: (user: User) => void){

}
