import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { RequestStatusDetailsUpdateComponent } from 'app/entities/request-status-details/request-status-details-update.component';
import { RequestStatusDetailsService } from 'app/entities/request-status-details/request-status-details.service';
import { RequestStatusDetails } from 'app/shared/model/request-status-details.model';

describe('Component Tests', () => {
  describe('RequestStatusDetails Management Update Component', () => {
    let comp: RequestStatusDetailsUpdateComponent;
    let fixture: ComponentFixture<RequestStatusDetailsUpdateComponent>;
    let service: RequestStatusDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [RequestStatusDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RequestStatusDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequestStatusDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequestStatusDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RequestStatusDetails(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RequestStatusDetails();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
