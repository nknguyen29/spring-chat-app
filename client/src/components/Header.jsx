import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

import {
  Menu,
  Home,
  CircleUser,
  Users,
  RefreshCw,
  MapPinned,
  MessageCirclePlus,
  HeartHandshake,
} from "lucide-react";

import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet";

import ThemeModeToggle from "./ThemeModeToggle";

import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function App({ user }) {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: () => {},
    onError: (error) => {
      console.log("[Header] onError: ", error);
    },
    onSettled: (data, error) => {
      console.log("[Header] onSettled: ", data, error);
      // invalidate the query to refetch the data
      queryClient.invalidateQueries([
        { queryKey: ["chatrooms"] },
        { queryKey: ["userChatrooms"] },
        { queryKey: ["users"] },
      ]);
    },
  });

  function handleRefresh() {
    mutation.mutate();
  }

  const [selectedLink, setSelectedLink] = useState("/");

  const linkClass = (path) =>
    `flex items-center gap-3 rounded-lg px-3 py-2 transition-all hover:text-primary ${
      selectedLink === path ? "bg-muted text-primary" : "text-muted-foreground"
    }`;

  return (
    <header className="flex h-14 items-center gap-4 border-b bg-muted/40 px-4 lg:h-[60px] lg:px-6">
      <Sheet>
        <SheetTrigger asChild>
          <Button variant="outline" size="icon" className="shrink-0 md:hidden">
            <Menu className="h-5 w-5" />
            <span className="sr-only">Toggle navigation menu</span>
          </Button>
        </SheetTrigger>
        <SheetContent side="left" className="flex flex-col">
          <nav className="grid gap-2 text-lg font-medium">
            <Link
              to="my-chatrooms"
              className={linkClass("my-chatrooms")}
              onClick={() => setSelectedLink("my-chatrooms")}
            >
              <Home className="h-4 w-4" />
              My Chatrooms
            </Link>
            <Link
              to="my-invitations"
              className={linkClass("my-invitations")}
              onClick={() => setSelectedLink("my-invitations")}
            >
              <HeartHandshake className="h-4 w-4" />
              My Invitations
            </Link>
            <Link
              to="create/chatroom"
              className={linkClass("create/chatroom")}
              onClick={() => setSelectedLink("create/chatroom")}
            >
              <MessageCirclePlus className="h-4 w-4" />
              Plan a Discussion
            </Link>
            <Link
              to="chatrooms"
              className={linkClass("chatrooms")}
              onClick={() => setSelectedLink("chatrooms")}
            >
              <MapPinned className="h-4 w-4" />
              Available Chatrooms
            </Link>
            <Link
              to="users"
              className={linkClass("users")}
              onClick={() => setSelectedLink("users")}
            >
              <Users className="h-4 w-4" />
              All Users
            </Link>
          </nav>
        </SheetContent>
      </Sheet>

      <div className="w-full flex-1"></div>

      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button variant="ghost" size="icon">
            <RefreshCw className="h-5 w-5" />
            <span className="sr-only">Refresh Data</span>
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end">
          <DropdownMenuLabel>
            <div>Refresh Data</div>
          </DropdownMenuLabel>
          <DropdownMenuSeparator />
          <DropdownMenuItem>
            <Button variant="ghost" onClick={handleRefresh}>
              Refresh Now
            </Button>
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>

      <ThemeModeToggle />
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button variant="outline" size="icon">
            <CircleUser className="h-5 w-5" />
            <span className="sr-only">Toggle user menu</span>
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end">
          <DropdownMenuLabel>
            <div>
              {user.firstName} {user.lastName}
            </div>
          </DropdownMenuLabel>
          <DropdownMenuSeparator />
          <Link to="/logout">
            <DropdownMenuItem>Logout</DropdownMenuItem>
          </Link>
        </DropdownMenuContent>
      </DropdownMenu>
    </header>
  );
}
