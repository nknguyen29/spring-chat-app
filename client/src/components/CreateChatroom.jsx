"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";

import { Label } from "@/components/ui/label";

import { format, formatISO, parseISO, addDays } from "date-fns";
import { Calendar as CalendarIcon } from "lucide-react";

import { cn } from "@/lib/utils";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";

import React from "react";

import axios from "axios";

const FormSchema = z.object({
  title: z.string().min(1, {
    message: "Title must not be empty",
  }),
  description: z.string().min(1, {
    message: "Description must not be empty",
  }),
});

export default function InputForm({ user }) {
  const form = useForm({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      title: "My New Chatroom",
      description: "My New Chatroom Description",
      startDate: new Date().toISOString(),
      validityDuration: null,
      createdById: 0,
    },
  });

  function onSubmit(data) {
    data.startDate = new Date().toISOString();
    if (data.validityDuration === undefined || data.validityDuration === null) {
      console.log("Problem with validitiy duration... setting it to 30 days");
      data.validityDuration = formatISO(addDays(new Date(), 30));
    }
    data.createdById = user.id;

    console.log({chatroom: data, userIds : [0]});

    console.log("Creating a new Chatroom...");
    axios
      .post("http://localhost:8080/api/chatrooms/", {chatroom: data, userIds : [0]})
      .then((res) => {
        console.log("Response from backend : ", res);
      })
      .catch((error) => {
        console.error("Error from backend : ", error.response);
      });
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="w-2/3 space-y-6">
        <FormField
          control={form.control}
          name="title"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Title</FormLabel>
              <FormControl>
                <Input placeholder="Title" {...field} />
              </FormControl>
              <FormDescription>
                This is the title of your new chatroom
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="description"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Description</FormLabel>
              <FormControl>
                <Input placeholder="Description" {...field} />
              </FormControl>
              <FormDescription>
                The description of your chatroom
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="validityDuration"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Validity Duration</FormLabel>
              <FormControl>
                <div>
                  <Popover>
                    <PopoverTrigger asChild>
                      <Button
                        variant={"outline"}
                        className={cn(
                          "w-[280px] justify-start text-left font-normal",
                          !field.value && "text-muted-foreground"
                        )}
                      >
                        <CalendarIcon className="mr-2 h-4 w-4" />
                        {field.value ? (
                          format(field.value, "PPP")
                        ) : (
                          <span>Pick a date</span>
                        )}
                      </Button>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0">
                      <Calendar
                        mode="single"
                        selected={
                          field.value ? parseISO(field.value) : undefined
                        }
                        onSelect={(date) =>
                          field.onChange(formatISO(addDays(date, 1)))
                        }
                        initialFocus
                      />
                    </PopoverContent>
                  </Popover>
                </div>
              </FormControl>
              <FormDescription>End Date of the Chatroom</FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">Submit</Button>
      </form>
    </Form>
  );
}
