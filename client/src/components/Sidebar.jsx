import React, { useState } from "react";
import { Link } from 'react-router-dom';

// import useGetChatrooms from '../hooks/useGetChatrooms';

import {
  Home,
  LineChart,
  Users,
  MessageCircleCode,
  MapPinned,
  MessageCircleMore,
} from "lucide-react"

import { Button } from "@/components/ui/button"

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"


export default function App({user}) {
  const [selectedLink, setSelectedLink] = useState("/");

  const linkClass = (path) => 
    `flex items-center gap-3 rounded-lg px-3 py-2 transition-all hover:text-primary ${
      selectedLink === path ? "bg-muted text-primary" : "text-muted-foreground"
    }`;
    
  return (
    <div className="grid min-h-screen w-full md:grid-cols-[220px_1fr] lg:grid-cols-[280px_1fr]">
      <div className="hidden border-r bg-muted/40 md:block">
        <div className="flex h-full max-h-screen flex-col gap-2">
          <div className="flex h-14 items-center border-b px-4 lg:h-[60px] lg:px-6">
            <Link to="/" className="flex items-center gap-2 font-semibold">
              <MessageCircleCode className="h-6 w-6" />
              <span className="">Diskette</span>
            </Link>
          </div>
          <div className="flex-1">
            <nav className="grid items-start px-2 text-sm font-medium lg:px-4">
              <Link
                to="my-chatrooms"
                className={linkClass("my-chatrooms")}
                onClick={() => setSelectedLink("my-chatrooms")}
              >
                <Home className="h-4 w-4" />
                My Chatrooms
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
                All Users{" "}
              </Link>
              <Link
                to="debugging"
                className={linkClass("debugging")}
                onClick={() => setSelectedLink("debugging")}
              >
                <LineChart className="h-4 w-4" />
                Debugging
              </Link>
              <Link
                to="discussion/1"
                className={linkClass("discussion")}
                onClick={() => setSelectedLink("discussion")}
              >
                <MessageCircleMore className="h-4 w-4" />
                Chat
              </Link>
            </nav>
          </div>
          <div className="mt-auto p-4">
            <Card x-chunk="dashboard-02-chunk-0">
              <CardHeader className="p-2 pt-0 md:p-4">
                <CardTitle>Dashboard</CardTitle>
                <CardDescription>
                  Checkout our new Dashboard, with advanced account management features !
                </CardDescription>
              </CardHeader>
              <CardContent className="p-2 pt-0 md:p-4 md:pt-0">
                <Link to="http://localhost:8080">
                <Button size="sm" className="w-full">
                  Go To Dashboard
                </Button>
                </Link>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </div>
  )
}
