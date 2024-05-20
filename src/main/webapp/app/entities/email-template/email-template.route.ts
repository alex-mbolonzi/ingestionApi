import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmailTemplate } from 'app/shared/model/email-template.model';
import { EmailTemplateService } from './email-template.service';
import { EmailTemplateComponent } from './email-template.component';
import { EmailTemplateDetailComponent } from './email-template-detail.component';
import { EmailTemplateUpdateComponent } from './email-template-update.component';
import { EmailTemplateDeletePopupComponent } from './email-template-delete-dialog.component';
import { IEmailTemplate } from 'app/shared/model/email-template.model';

@Injectable({ providedIn: 'root' })
export class EmailTemplateResolve implements Resolve<IEmailTemplate> {
  constructor(private service: EmailTemplateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmailTemplate> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EmailTemplate>) => response.ok),
        map((emailTemplate: HttpResponse<EmailTemplate>) => emailTemplate.body),
      );
    }
    return of(new EmailTemplate());
  }
}

export const emailTemplateRoute: Routes = [
  {
    path: '',
    component: EmailTemplateComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.emailTemplate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmailTemplateDetailComponent,
    resolve: {
      emailTemplate: EmailTemplateResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.emailTemplate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmailTemplateUpdateComponent,
    resolve: {
      emailTemplate: EmailTemplateResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.emailTemplate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmailTemplateUpdateComponent,
    resolve: {
      emailTemplate: EmailTemplateResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.emailTemplate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const emailTemplatePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmailTemplateDeletePopupComponent,
    resolve: {
      emailTemplate: EmailTemplateResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.emailTemplate.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
