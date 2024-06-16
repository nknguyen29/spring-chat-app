import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

import { useContext, useEffect } from "react";
import { AuthContext } from "./AuthContext";
import { useNavigate } from "react-router-dom";

export default function Home() {
  const navigate = useNavigate();

  const { isAuthenticated } = useContext(AuthContext);
  useEffect(() => {
    if (!isAuthenticated) {
      // go to the login page
      navigate("/login");
    }
  }, [isAuthenticated, navigate]);

  return (
    <div>
      <Card>
        <CardHeader className="px-7">
          <CardTitle>Welcome to the Chatroom Application</CardTitle>
        </CardHeader>
        <CardContent>
          This application is a simple chatroom application that allows users to
          create chatrooms and chat with other users.
        </CardContent>
        <CardContent>
            Start by interacting with the sidebar !
        </CardContent>
      </Card>
    </div>
  );
}
